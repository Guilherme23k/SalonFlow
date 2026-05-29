

interface Props {
  customerName: string;
  customerPhone: string;
  onNameChange: (value: string) => void;
  onPhoneChange: (value: string) => void;
  onSubmit: () => void;
  loading: boolean;
}

export default function BookingForm({
    customerName,
    customerPhone,
    onNameChange,
    onPhoneChange,
    onSubmit,
    loading
}: Props) {
    const isValid = customerName.trim().length > 0 && customerPhone.trim().length > 0;

  return (
    <div className="flex flex-col gap-4">
      <div className="flex flex-col gap-1.5">
        <label className="text-sm text-zinc-400">Seu nome</label>
        <input
          type="text"
          value={customerName}
          onChange={(e) => onNameChange(e.target.value)}
          placeholder="João Silva"
          className="bg-zinc-900 border border-zinc-700 text-white rounded-lg px-4 py-2.5
            placeholder:text-zinc-600 focus:outline-none focus:border-violet-500"
        />
      </div>

      <div className="flex flex-col gap-1.5">
        <label className="text-sm text-zinc-400">Seu telefone</label>
        <input
          type="tel"
          value={customerPhone}
          onChange={(e) => onPhoneChange(e.target.value)}
          placeholder="(11) 99999-0000"
          className="bg-zinc-900 border border-zinc-700 text-white rounded-lg px-4 py-2.5
            placeholder:text-zinc-600 focus:outline-none focus:border-violet-500"
        />
      </div>

      <button
        onClick={onSubmit}
        disabled={!isValid || loading}
        className="w-full py-3 rounded-lg font-medium transition-colors
          bg-violet-500 text-white hover:bg-violet-600
          disabled:bg-zinc-800 disabled:text-zinc-600 disabled:cursor-not-allowed"
      >
        {loading ? 'Confirmando...' : 'Confirmar agendamento'}
      </button>
    </div>
  );
}