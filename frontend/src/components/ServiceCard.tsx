import type { Service } from "../types";

interface Props{
    service: Service;
    selected: boolean;
    onSelect: (service: Service) => void;
}

export default function ServiceCard({ service, selected, onSelect }: Props) {

    return (
        <button
        onClick={() => onSelect(service)}
        className={`w-full text-left px-4 py-3 rounded-lg border transition-colors
        ${selected
          ? 'border-violet-500 bg-violet-500/10 text-white'
          : 'border-zinc-700 bg-zinc-900 text-zinc-300 hover:border-zinc-500'
        }`}
        >

        <p className="font-medium">{service.name}</p>
        {service.description && (
        <p className="text-sm text-zinc-500 mt-0.5">{service.description}</p>
        )}

        </button>
    )

}