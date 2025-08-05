import { useState } from 'react';
import Deposito from '../Deposito';
import Saque from '../Saque';
import Pagamento from '../Pagamento';
import Extrato from '../Extrato';

function Operacoes() {

    const [operacaoSelecionada, setOperacaoSelecionada] = useState('');

    const items = [
        <Deposito key="deposito" id="deposito" />,
        <Saque key="saque" id="saque" />,
        <Pagamento key="pagamento" id="pagamento" />,
        <Extrato key="extrato" id="extrato" />
    ];

    return (
        <>
            <button onClick={() => setOperacaoSelecionada('deposito')}>Depósito</button>
            <button onClick={() => setOperacaoSelecionada('saque')}>Saque</button>
            <button onClick={() => setOperacaoSelecionada('pagamento')}>Pagamento</button>
            <button onClick={() => setOperacaoSelecionada('deposito')}>Extrato</button>
            <section>
                {items.find(i => i.props.id === operacaoSelecionada)}
            </section>
        </>
    );
} export default Operacoes