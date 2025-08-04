import axios from '../../services/api';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';

function Login() {

    const navigate = useNavigate()

    //TODO: Evitar repetição de código com Cadastro
    const [usuario, setUsuario] = useState({
        email: '',
        senha: ''
    })

    const handleInput = (event) => {
        setUsuario({ ...usuario, [event.target.name]: event.target.event })
    }

    const handleSubmit = async (event) => {
        event.preventDefault()
        try {
            const res = await axios.post("/posts", { usuario })
            localStorage.setItem("token", res.data.token)
            navigate("/contacorrente")
        } catch (err) {
            setErro("Credenciais inválidas")
        }
    }

    //TODO: Estilizar
    return (
        <form onSubmit={handleSubmit}>
            E-mail: <input input type="text" onChange={handleInput} name="email"></input>
            Senha: <input input type="text" onChange={handleInput} name="senha"></input>
            <button>Entrar</button>
        </form>
    );
} export default Login;