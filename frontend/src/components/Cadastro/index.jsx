import axios from '../../services/api';
import {useState} from 'react';
import {useNavigate} from 'react-router-dom';

function Cadastro(){

    const navigate = useNavigate()

    const [usuario, setUsuario] = useState({
        nome:'', 
        cpf:'', 
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

    return(
        <form onSubmit={handleSubmit}>
            Nome: <input input type="text" onChange={handleInput} name="nome"></input>
            CPF: <input input type="text" onChange={handleInput} name="cpf"></input>
            E-mail: <input input type="text" onChange={handleInput} name="email"></input>
            Senha: <input input type="text" onChange={handleInput} name="senha"></input>
            <button>Cadastrar</button>
        </form>
    );
}export default Cadastro;