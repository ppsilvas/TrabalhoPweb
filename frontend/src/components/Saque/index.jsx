import useAxiosPrivate from "../../hooks/useAxiosPrivate";
import { useState } from "react";
import useAuth from "../../hooks/useAuth";

function Saque({ onOperacaoConcluida, OPERACOES_URL }) {

    const { auth } = useAuth();
    const [conta, setConta] = useState('');
    const [valor, setValor] = useState('');
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
            const response = await axiosPrivate.post(OPERACOES_URL + `/${usuarioId}/saque`,
                { valor, numConta: parseInt(conta), tipo: 'SAQUE', descricao: 'Operação de saque.', id: usuarioId},
                {params: {usuarioId}}
            );
            onOperacaoConcluida();
            setMsg('Saque realizado com sucesso!');
            setConta('');
            setValor('');
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
                Conta para sacar:
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
                Valor do saque:
            </label>
            <input
                type="number"
                value={valor}
                onChange={(event) => setValor(event.target.value)}
                id="valor"
                autoComplete="off"
                required
            />
            <button>Efetuar Saque</button>
            {msg && <p className="mensagem">{msg}</p>}
        </form>
    );
} export default Saque;