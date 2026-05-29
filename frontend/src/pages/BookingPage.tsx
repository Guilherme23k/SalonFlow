// src/pages/BookingPage.tsx
import { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import {
  resolveTenant,
  getProfessionals,
  getServices,
  getAvailability,
  createAppointment,
} from '../api/salonflow';
import type {
  Tenant,
  Professional,
  Service,
  AvailableSlot,
  AppointmentResponse,
} from '../types';
import ProfessionalCard from '../components/ProfessionalCard';
import ServiceCard from '../components/ServiceCard';
import DatePicker from '../components/DatePicker';
import SlotGrid from '../components/SlotGrid';
import BookingForm from '../components/BookingForm';

type Step = 'professional' | 'service' | 'date' | 'slot' | 'form' | 'confirmation';

export default function BookingPage() {
  const { slug } = useParams<{ slug: string }>();

  // Tenant
  const [tenant, setTenant] = useState<Tenant | null>(null);
  const [tenantLoading, setTenantLoading] = useState(true);
  const [tenantError, setTenantError] = useState(false);

  // Dados
  const [professionals, setProfessionals] = useState<Professional[]>([]);
  const [services, setServices] = useState<Service[]>([]);
  const [slots, setSlots] = useState<AvailableSlot[]>([]);

  // Seleções
  const [selectedProfessional, setSelectedProfessional] = useState<Professional | null>(null);
  const [selectedService, setSelectedService] = useState<Service | null>(null);
  const [selectedDate, setSelectedDate] = useState('');
  const [selectedSlot, setSelectedSlot] = useState<string | null>(null);
  const [customerName, setCustomerName] = useState('');
  const [customerPhone, setCustomerPhone] = useState('');

  // UI
  const [step, setStep] = useState<Step>('professional');
  const [loading, setLoading] = useState(false);
  const [confirmation, setConfirmation] = useState<AppointmentResponse | null>(null);

  // Resolve o tenant pelo slug
  useEffect(() => {
    if (!slug) return;
    resolveTenant(slug)
      .then((t) => {
        setTenant(t);
        return getProfessionals();
      })
      .then(setProfessionals)
      .catch(() => setTenantError(true))
      .finally(() => setTenantLoading(false));
  }, [slug]);

  // Busca serviços quando profissional é selecionado
  useEffect(() => {
  if (!selectedProfessional) return;

  async function fetchServices() {
    setLoading(true);
    try {
      const data = await getServices();
      setServices(data);
    } finally {
      setLoading(false);
    }
  }

  void fetchServices();
}, [selectedProfessional]);

  // Busca slots quando data é selecionada
  useEffect(() => {
  if (!selectedProfessional || !selectedService || !selectedDate) return;

  async function fetchSlots() {
    setLoading(true);
    setSlots([]);
    setSelectedSlot(null);
    try {
      const data = await getAvailability(
        selectedProfessional!.id,
        selectedService!.id,
        selectedDate,
      );
      setSlots(data);
    } finally {
      setLoading(false);
    }
  }

  void fetchSlots();
}, [selectedProfessional, selectedService, selectedDate]);

  function handleSelectProfessional(professional: Professional) {
    setSelectedProfessional(professional);
    setSelectedService(null);
    setSelectedDate('');
    setSelectedSlot(null);
    setStep('service');
  }

  function handleSelectService(service: Service) {
    setSelectedService(service);
    setSelectedDate('');
    setSelectedSlot(null);
    setStep('date');
  }

  function handleSelectDate(date: string) {
    setSelectedDate(date);
    setStep('slot');
  }

  function handleSelectSlot(time: string) {
    setSelectedSlot(time);
    setStep('form');
  }

  async function handleConfirm() {
    if (!selectedProfessional || !selectedService || !selectedDate || !selectedSlot) return;

    setLoading(true);
    try {
      const scheduledAt = `${selectedDate}T${selectedSlot}:00`;
      const result = await createAppointment({
        professionalId: selectedProfessional.id,
        serviceId: selectedService.id,
        customerName,
        customerPhone,
        scheduledAt,
      });
      setConfirmation(result);
      setStep('confirmation');
    } catch {
      alert('Este horário não está mais disponível. Por favor, escolha outro.');
      setStep('slot');
      setSelectedSlot(null);
    } finally {
      setLoading(false);
    }
  }

  function handleBack() {
    const backMap: Record<Step, Step> = {
      professional: 'professional',
      service: 'professional',
      date: 'service',
      slot: 'date',
      form: 'slot',
      confirmation: 'confirmation',
    };
    setStep(backMap[step]);
  }

  // Estados de carregamento e erro do tenant
  if (tenantLoading) {
    return (
      <div className="min-h-screen bg-zinc-950 flex items-center justify-center">
        <p className="text-zinc-400 animate-pulse">Carregando...</p>
      </div>
    );
  }

  if (tenantError || !tenant) {
    return (
      <div className="min-h-screen bg-zinc-950 flex items-center justify-center">
        <p className="text-red-400">Salão não encontrado.</p>
      </div>
    );
  }

  // Tela de confirmação
  if (step === 'confirmation' && confirmation) {
    return (
      <div className="min-h-screen bg-zinc-950 text-white flex flex-col items-center justify-center px-4">
        <div className="max-w-sm w-full text-center flex flex-col gap-4">
          <div className="w-16 h-16 rounded-full bg-violet-500/20 flex items-center justify-center mx-auto">
            <span className="text-3xl">✓</span>
          </div>
          <h2 className="text-2xl font-semibold">Agendado!</h2>
          <p className="text-zinc-400">
            Seu atendimento foi confirmado com sucesso.
          </p>
          <div className="bg-zinc-900 border border-zinc-800 rounded-lg px-4 py-4 text-left flex flex-col gap-2 text-sm">
            <Row label="Profissional" value={confirmation.professionalName} />
            <Row label="Serviço" value={confirmation.serviceName} />
            <Row label="Data" value={formatDate(confirmation.scheduledAt)} />
            <Row label="Horário" value={formatTime(confirmation.scheduledAt)} />
            <Row label="Duração" value={`${confirmation.durationMinutes} minutos`} />
          </div>
          <button
            onClick={() => {
              setStep('professional');
              setSelectedProfessional(null);
              setSelectedService(null);
              setSelectedDate('');
              setSelectedSlot(null);
              setCustomerName('');
              setCustomerPhone('');
              setConfirmation(null);
            }}
            className="text-sm text-zinc-500 hover:text-zinc-300 transition-colors"
          >
            Fazer outro agendamento
          </button>
        </div>
      </div>
    );
  }

  const stepTitles: Record<Step, string> = {
    professional: 'Escolha o profissional',
    service: 'Escolha o serviço',
    date: 'Escolha a data',
    slot: 'Escolha o horário',
    form: 'Seus dados',
    confirmation: '',
  };

  return (
    <div className="min-h-screen bg-zinc-950 text-white">
      {/* Header */}
      <header className="border-b border-zinc-800 px-6 py-4">
        <h1 className="text-xl font-semibold">{tenant.name}</h1>
        <p className="text-sm text-zinc-400">
          Atendimento das {tenant.openingTime.substring(0, 5)} às{' '}
          {tenant.closingTime.substring(0, 5)}
        </p>
      </header>

      <main className="max-w-lg mx-auto px-4 py-8 flex flex-col gap-6">
        {/* Breadcrumb de seleções */}
        {(selectedProfessional || selectedService || selectedDate || selectedSlot) && (
          <div className="flex flex-wrap gap-2 text-xs">
            {selectedProfessional && (
              <Chip label={selectedProfessional.name} onClick={() => setStep('professional')} />
            )}
            {selectedService && (
              <Chip label={selectedService.name} onClick={() => setStep('service')} />
            )}
            {selectedDate && (
              <Chip label={formatDate(selectedDate)} onClick={() => setStep('date')} />
            )}
            {selectedSlot && (
              <Chip label={selectedSlot} onClick={() => setStep('slot')} />
            )}
          </div>
        )}

        {/* Título da etapa */}
        <div className="flex items-center gap-3">
          {step !== 'professional' && (
            <button
              onClick={handleBack}
              className="text-zinc-400 hover:text-white transition-colors text-sm"
            >
              ← Voltar
            </button>
          )}
          <h2 className="text-lg font-medium">{stepTitles[step]}</h2>
        </div>

        {/* Etapa 1 — Profissional */}
        {step === 'professional' && (
          <div className="flex flex-col gap-3">
            {professionals.filter((p) => p.active).map((p) => (
              <ProfessionalCard
                key={p.id}
                professional={p}
                selected={selectedProfessional?.id === p.id}
                onSelect={handleSelectProfessional}
              />
            ))}
          </div>
        )}

        {/* Etapa 2 — Serviço */}
        {step === 'service' && (
          <div className="flex flex-col gap-3">
            {loading ? (
              <p className="text-zinc-500 text-sm animate-pulse">Carregando serviços...</p>
            ) : (
              services.filter((s) => s.active).map((s) => (
                <ServiceCard
                  key={s.id}
                  service={s}
                  selected={selectedService?.id === s.id}
                  onSelect={handleSelectService}
                />
              ))
            )}
          </div>
        )}

        {/* Etapa 3 — Data */}
        {step === 'date' && (
          <div className="flex flex-col gap-4">
            <DatePicker value={selectedDate} onChange={handleSelectDate} />
          </div>
        )}

        {/* Etapa 4 — Slot */}
        {step === 'slot' && (
          <div className="flex flex-col gap-4">
            {loading ? (
              <p className="text-zinc-500 text-sm animate-pulse">Buscando horários...</p>
            ) : (
              <SlotGrid
                slots={slots}
                selected={selectedSlot}
                onSelect={handleSelectSlot}
              />
            )}
          </div>
        )}

        {/* Etapa 5 — Formulário */}
        {step === 'form' && (
          <BookingForm
            customerName={customerName}
            customerPhone={customerPhone}
            onNameChange={setCustomerName}
            onPhoneChange={setCustomerPhone}
            onSubmit={handleConfirm}
            loading={loading}
          />
        )}
      </main>
    </div>
  );
}

// Componentes auxiliares internos
function Row({ label, value }: { label: string; value: string }) {
  return (
    <div className="flex justify-between">
      <span className="text-zinc-500">{label}</span>
      <span className="text-white font-medium">{value}</span>
    </div>
  );
}

function Chip({ label, onClick }: { label: string; onClick: () => void }) {
  return (
    <button
      onClick={onClick}
      className="px-2.5 py-1 rounded-full bg-zinc-800 text-zinc-400
        hover:bg-zinc-700 hover:text-white transition-colors"
    >
      {label}
    </button>
  );
}

function formatDate(dateStr: string): string {
  const date = new Date(dateStr + (dateStr.length === 10 ? 'T00:00:00' : ''));
  return date.toLocaleDateString('pt-BR', {
    day: '2-digit',
    month: '2-digit',
    year: 'numeric',
  });
}

function formatTime(dateStr: string): string {
  return dateStr.substring(11, 16);
}