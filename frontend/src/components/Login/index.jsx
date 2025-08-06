import axios from '../../services/api';
import { useState } from 'react';
import { Link, useNavigate, useLocation } from 'react-router-dom';
import useAuth from '../../hooks/useAuth';
import { jwtDecode } from 'jwt-decode';

function Login() {
    const { setAuth } = useAuth();

    const navigate = useNavigate()
    const location = useLocation();
    const from = location.state?.from?.pathname || '/contacorrente';

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
            const token = res.data.token;
            const decoded = jwtDecode(token);
            const id = decoded.userId;
            setAuth({ accessToken: token, id });
            navigate(from, { replace: true });
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