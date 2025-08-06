import useAxiosPrivate from "../../hooks/useAxiosPrivate";
import { useState } from "react";
import useAuth from "../../hooks/useAuth";

function Pagamento({ onOperacaoConcluida, OPERACOES_URL }) {

    const { auth } = useAuth();
    const [conta, setConta] = useState('');
    const [valor, setValor] = useState('');
    const [descricao, setDescricao] = useState('');
    const [msg, setMsg] = useState('');

    const axiosPrivate = useAxiosPrivate();

    const usuarioId = auth.id;

    const handleSubmit = async (event) => {
        event.preventDefault();

        if (valor <= 0) {
            setMsg('O valor deve ser maior que zero.');
            return;
        }

        console.log(auth.accessToken);

        try {
            const response = await axiosPrivate.post(OPERACOES_URL + `/${usuarioId}/pagamento`,
                { valor, contaId: parseInt(conta), tipo: 'PAGAMENTO', descricao, usuarioId }
            );
            onOperacaoConcluida();
            setMsg('Pagamento realizado com sucesso!');
            setConta('');
            setValor('');
            setDescricao('');
            console.log(response.data);
        }
        catch (err) {
            setMsg('Houve um erro na operação');
            console.error(err);
        }
    }

    return (
        <form onSubmit={handleSubmit}>
            <label htmlFor="conta">
                Conta para pagamento:
            </label>
            <input
                type="text"
                value={conta}
                onChange={(event) => setConta(event.target.value)}
                id="conta"
                autoComplete="off"
                required
            />
            <label htmlFor="valor">
                Valor do pagamento:
            </label>
            <input
                type="number"
                value={valor}
                onChange={(event) => setValor(event.target.value)}
                id="valor"
                autoComplete="off"
                required
            />
            <label htmlFor="desricao">
                Descrição:
            </label>
            <textarea
                value={descricao}
                onChange={(event) => setDescricao(event.target.value)}
                id="descricao"
                autoComplete="off"
                rows={2}
                required
            />
            <button>Efetuar Pagamento</button>
            {msg && <p className="mensagem">{msg}</p>}
        </form>
    );
} export default Pagamento;