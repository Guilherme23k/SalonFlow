import type { AvailableSlot } from "../types";

interface Props{
    slots: AvailableSlot[];
    selected: string | null;
    onSelect: (time:string) => void;
}

export default function SlotGrid({slots, selected, onSelect}: Props){
    if (slots.length === 0) {
    return (
      <p className="text-zinc-500 text-sm">
        Nenhum horário disponível para esta data.
      </p>
    );
  }

  return (
    <div className="grid grid-cols-4 gap-2">
      {slots.map((slot) => (
        <button
          key={slot.time}
          disabled={!slot.available}
          onClick={() => slot.available && onSelect(slot.time)}
          className={`py-2 rounded-lg text-sm font-medium transition-colors
            ${!slot.available
              ? 'bg-zinc-800 text-zinc-600 line-through cursor-not-allowed'
              : selected === slot.time
                ? 'bg-violet-500 text-white'
                : 'bg-zinc-900 border border-zinc-700 text-zinc-300 hover:border-violet-500'
            }`}
        >
          {slot.time}
        </button>
      ))}
    </div>
  );
}