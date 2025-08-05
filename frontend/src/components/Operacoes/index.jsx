import Deposito from '../Deposito';
import Saque from '../Saque';
import Pagamento from '../Pagamento';
import Extrato from '../Extrato';

function Operacoes(props) {

    const items = [
        <Deposito key="deposito" id="deposito" />,
        <Saque key="saque" id="saque" />,
        <Pagamento key="pagamento" id="pagamento" />,
        <Extrato key="extrato" id="extrato" />
    ];

    return (
        <section>
            {items.find(i => i.props.id === props.operacaoSelecionada)}
        </section>
    );
} export default Operacoes