import axios from '../../services/api';
import { useState } from 'react';
//import {useNavigate} from 'react-router-dom';

function Cadastro() {

    const [nome, setNome] = useState('');
    const [email, setEmail] = useState('');
    const [cpf, setCpf] = useState('');
    const [senha, setSenha] = useState('');

    const REGISTER_URL = '/usuarios';

    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            const response = await axios.post(REGISTER_URL,
                { nome, email, cpf, senha },
                {
                    headers: { 'Content-Type': 'application/json' },
                    //withCredentials: true
                }
            );
            console.log(response.data);
        }
        catch (err) {
            console.error(err);
        }
    }

    return (
        <form onSubmit={handleSubmit}>
            <label htmlFor="nome">
                Nome:
            </label>
            <input
                type="text"
                onChange={(event) => setNome(event.target.value)}
                id="nome"
                autoComplete="off"
                required
            />
            <label htmlFor="cpf">
                CPF:
            </label>
            <input
                type="text"
                onChange={(event) => setCpf(event.target.value)}
                id="cpf"
                autoComplete="off"
                required
            />
            <label htmlFor="email">
                E-mail:
            </label>
            <input
                type="text"
                onChange={(event) => setEmail(event.target.value)}
                id="email"
                autoComplete="off"
                required
            />
            <label htmlFor="senha">
                Senha:
            </label>
            <input
                type="password"
                onChange={(event) => setSenha(event.target.value)}
                id="senha"
                required
            />
            <button>Cadastrar</button>
        </form>
    );
} export default Cadastro;