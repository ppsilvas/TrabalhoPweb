import { useEffect, useState } from 'react';
import useAxiosPrivate from '../../hooks/useAxiosPrivate';
import useAuth from '../../hooks/useAuth';
import Operacoes from '../Operacoes';

function ContaCorrente() {
    const { auth } = useAuth();
    const [conta, setConta] = useState(null);
    const [erro, setErro] = useState('');

    const axiosPrivate = useAxiosPrivate();

    useEffect(() => {
        const fetchConta = async () => {
            try {
                const response = await axiosPrivate.get(`/conta/${auth.id}`, {
                    headers: {
                        Authorization: `Bearer ${auth.accessToken}`
                    }
                });
                setConta(response.data);
            }
            catch (err) {
                console.error(err);
                setErro('Erro ao carregar conta');
            }
        };

        fetchConta();
    }, [auth]);

    if (erro) return <p>{erro}</p>
    if (!conta) return <p>Carregando...</p>

    return (
        <section>
            <h1>{conta.usuario}</h1>
            <p>Número: {conta.numero}</p>
            <p>Agência: {conta.agencia}</p>
            <p>Saldo: R${conta.saldo}</p>
            <Operacoes operacaoSelecionada />
        </section>
    );
} export default ContaCorrente;