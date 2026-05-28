interface Props{
    value: string;
    onChange: (date: string) => void;
}

export default function DatePicker({value, onChange}: Props){
    const today = new Date().toISOString().split('T')[0];

    return (
        <div className="flex flex-col gap-2">
            <label className="text-sm text-zinc-400">Escolha uma data</label>
            <input
        type="date"
        value={value}
        min={today}
        onChange={(e) => onChange(e.target.value)}
        className="bg-zinc-900 border border-zinc-700 text-white rounded-lg px-4 py-2.5
          focus:outline-none focus:border-violet-500 cursor-pointer
          [color-scheme:dark]"
      />
        </div>
    )
}