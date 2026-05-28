import { useParams } from "react-router-dom";
import type { Tenant } from "../types";
import { useEffect, useState } from "react";
import { resolveTenant } from "../api/salonflow";

export default function BookingPage(){
    const { slug } = useParams<{ slug: string }>();
    const [tenant, setTenant] = useState<Tenant | null>(null);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(false);

    useEffect(() => {
    if (!slug) return;

    resolveTenant(slug)
      .then(setTenant)
      .catch(() => setError(true))
      .finally(() => setLoading(false));
  }, [slug]);

  if (loading) {
    return (
      <div className="min-h-screen bg-zinc-950 flex items-center justify-center">
        <p className="text-zinc-400 animate-pulse">Carregando...</p>
      </div>
    );
  }

  if (error || !tenant) {
    return (
      <div className="min-h-screen bg-zinc-950 flex items-center justify-center">
        <p className="text-red-400">Salão não encontrado.</p>
      </div>
    );
  }

  return (
    <div className="min-h-screen bg-zinc-950 text-white">
      <header className="border-b border-zinc-800 px-6 py-4">
        <h1 className="text-xl font-semibold">{tenant.name}</h1>
        <p className="text-sm text-zinc-400">Agende seu atendimento</p>
      </header>

      <main className="max-w-lg mx-auto px-4 py-8">
        <p className="text-zinc-400 text-sm">
          Horário de atendimento:{' '}
          <span className="text-white">
            {tenant.openingTime.substring(0, 5)} às{' '}
            {tenant.closingTime.substring(0, 5)}
          </span>
        </p>
      </main>
    </div>
  );
}