import React, { useRef, useState } from 'react';
import { FunctionComponent } from 'react';

type Props = {
  setCity: (city: string) => void;
};

const CitySelector: FunctionComponent<Props> = (props: Props) => {

  const { setCity } = props;

  const inputRef = useRef(null);
  const [input, setInput] = useState<string | null>(null);

  return (
    <>
      <div className='text-2xl'>Input city name:</div>
      <input
        type="text"
        ref={inputRef}
        onChange={
          (e) => setInput(e.target.value)
        }
        placeholder={`city name`}
        className="mt-10 text-4xl rounded-full border-2 border-gray-500"
      />

      <button
        onClick={() => setCity(input!)}
        disabled={!input}
        className="text-2xl bg-gray-500 hover:bg-gray-700 px-5 py-2 leading-5 rounded-full font-semibold text-white"
      >
        Set
      </button>
      <button
        onClick={() => {
          setInput(null);
          setCity("");
          // @ts-ignore
          inputRef.current.value = "";
        }}
        disabled={!input}
        className="text-2xl bg-gray-500 hover:bg-gray-700 px-5 py-2 leading-5 rounded-full font-semibold text-white"
      >
        Reset
      </button>
    </>
  )
}

export default CitySelector;