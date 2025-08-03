import axios from '../../services/api';
import {useState} from 'react';

function Login(){

    //TODO: Evitar repetição de código com Cadastro
    const [usuario, setUsuario] = useState({
        email:'', 
        senha:''
    })

    const handleInput = (event)=>{
        setUsuario({...usuario, [event.target.name]: event.target.event})
    }

    function handleSubmit(event){
        event.preventDefault()
        axios.post('/posts', {usuario})
        .then(response=>console.log(response))
        .catch(err=>console.log(err))
    }

    //TODO: Estilizar
    return(
        <form onSubmit={handleSubmit}>
            E-mail: <input input type="text" onChange={handleInput} name="email"></input>
            Senha: <input input type="text" onChange={handleInput} name="senha"></input>
            <button>Entrar</button>
        </form>
    );
}export default Login;