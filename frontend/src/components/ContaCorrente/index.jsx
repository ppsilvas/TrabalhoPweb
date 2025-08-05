import {useEffect,useState} from 'react';
import axios from '../../services/api';
import useAuth from '../../hooks/useAuth';
import Deposito from '../Deposito';

function ContaCorrente(){
    const {auth} = useAuth();
    const [conta, setConta] = useState(null);
    const [erro, setErro] = useState('');

    useEffect(()=>{
        const fetchConta = async()=>{
            try{
                const response = await axios.get(`/conta/${auth.id}`, {
                    headers: {
                        Authorization:'Bearer ${auth.accessToken'
                    }
                });
                setConta(response.data);
            }
            catch(err){
                console.error(err);
                setErro('Erro ao carregar conta.');
            }
        };

        fetchConta();
    },[auth]);

    if(erro) return <p>{erro}</p>
    if(!conta) return <p>Carregando...</p>

    return(
        <section>
            <h1>Olá, {conta.nome}!</h1>
            <p>Conta: {conta.numero}</p>
            <p>Agência: {conta.agencia}</p>
            <p>Saldo: R${conta.saldo.toFixed(2)}</p>
            <Deposito />
        </section>
    );
}export default ContaCorrente;