import axios from '../../services/api';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import useAuth from '../../hooks/useAuth';

function Login() {
    const { setAuth } = useAuth();

    const navigate = useNavigate()

    const [email, setEmail] = useState('');
    const [senha, setSenha] = useState('');

    const LOGIN_URL = '/auth';

    const handleSubmit = async (event) => {
        event.preventDefault();
        try {
            const res = await axios.post(LOGIN_URL,
                { email, senha },
                {
                    headers: { 'Content-Type': 'application/json' },
                    //withCredentials:true
                });
            const accessToken = res?.data?.accessToken;
            const roles = res?.data?.roles;
            setAuth({email,senha,roles,accessToken})
            console.log(res.data);
        } catch (err) {
            console.error(err);
        }
    }

    //TODO: Estilizar
    return (
        <form onSubmit={handleSubmit}>
            <label htmlFor="email">
                E-mail:
            </label>
            <input
                type="text"
                onChange={(event) => setEmail(event.target.value)}
                id="email"
                autoComplete="off"
                value={email}
                required
            />
            <label htmlFor="password">
                Senha:
            </label>
            <input
                type="password"
                onChange={(event) => setSenha(event.target.value)}
                id="senha"
                value={senha}
                required
            />
            <button>Entrar</button>
        </form>
    );
} export default Login;