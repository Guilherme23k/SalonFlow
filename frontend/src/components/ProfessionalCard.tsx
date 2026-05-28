import type { Professional } from "../types";

interface Props {
    professional: Professional;
    selected: boolean;
    onSelect: (professional: Professional) => void;
}

export default function ProfessionalCard({professional, selected, onSelect}: Props) {
    return (
        <button
        onClick={() => onSelect(professional)}
        className={`w-full text-left px-4 py-3 rounded-lg border transition-colors
        ${selected
          ? 'border-violet-500 bg-violet-500/10 text-white'
          : 'border-zinc-700 bg-zinc-900 text-zinc-300 hover:border-zinc-500'
        }`}
        >
           <p className="font-medium">{professional.name}</p>
        </button>
    )
}