import Deposito from '../Deposito';

function Operacoes(props) {

    const items = [
        <Deposito key="deposito" id="deposito" />
    ];

    return (
        <section>
            {items.find(i => i.props.id === props.operacaoSelecionada)}
        </section>
    );
} export default Operacoes