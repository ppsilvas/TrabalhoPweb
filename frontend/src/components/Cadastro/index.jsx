import axios from '../../services/api';
import {useState} from 'react';
//import {useNavigate} from 'react-router-dom';

function Cadastro(){

    const [nome, setNome] = useState('');
    const [email, setEmail] = useState('');
    const [cpf, setCpf] = useState('');
    const [senha, setSenha] = useState('');

    const REGISTER_URL = '/posts';

    const handleSubmit = async (event) =>{
        event.preventDefault();
        try{
            const response = await axios.post(REGISTER_URL,
                JSON.stringify({nome, email, cpf, senha}),
                {
                    headers:{'Content-Type':'application/json'},
                    withCredentials: true
                }
            );
            console.log(response.data);
        }
        catch(err){
            if(!err?.response){
                setErrMsg('Sem resposta do servidor');
            }
        }
    }

    return(
        <form onSubmit={handleSubmit}>
            <label htmlFor="nome">
                Nome:
            </label> 
            <input 
                type="text"
                onChange={(event)=>setNome(event.target.value)}
                id="nome"
                autoComplete="off"
                required
            />
            <label htmlFor="cpf">
                CPF:
            </label> 
            <input 
                type="text"
                onChange={(event)=>setCpf(event.target.value)} 
                id="cpf"
                autoComplete="off"
                required
            />
            <label htmlFor="email">
                E-mail:
            </label>            
            <input 
                type="text"
                onChange={(event)=>setEmail(event.target.value)}
                id="email"
                autoComplete="off"
                required
            />            
            <label htmlFor="senha">
                Senha:
            </label>  
            <input 
                type="text"
                onChange={(event)=>setSenha(event.target.value)} 
                id="senha"
                required
            /> 
            <button>Cadastrar</button>
        </form>
    );
}export default Cadastro;