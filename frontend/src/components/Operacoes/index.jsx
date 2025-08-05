import { useState } from 'react';
import Deposito from '../Deposito';
import Saque from '../Saque';
import Pagamento from '../Pagamento';
import Extrato from '../Extrato';

function Operacoes() {

    const [operacaoSelecionada, setOperacaoSelecionada] = useState('extrato');

    const items = [
        {id:'deposito', label: 'Depósito', componente: Deposito},
        {id:'saque', label: 'Saque', componente: Saque},
        {id:'pagamento', label: 'Pagamento', componente: Pagamento},
        {id:'extrato', label: 'Extrato', componente: Extrato}
    ];

    const OperacaoAtiva = items.find(i=>i.id===operacaoSelecionada)?.componente;

    return (
        <>
            <div>
                {items.map(i=>(
                    <button
                        key={i.id}
                        onClick={()=>setOperacaoSelecionada(i.id)}
                    >
                        {i.label}
                    </button>
                ))}
            </div>
            <section>
                {OperacaoAtiva && <OperacaoAtiva />}
            </section>
        </>
    );
} export default Operacoes