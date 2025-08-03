import axios from '../../services/api';
import {useState} from 'react';
import UserForm from '../UserForm';

function Cadastro(){

    const [usuario, setUsuario] = useState({
        nome:'', 
        cpf:'', 
        email:'', 
        senha:''
    })

    function handleSubmit(event){
        event.preventDefault()
        axios.post('/posts', {usuario})
        .then(response=>console.log(response))
        .catch(err=>console.log(err))
    }

    return(
        <UserForm
            usuario={usuario}
            setUsuario={setUsuario}
            handleSubmit={handleSubmit}
        />
    );
}export default Cadastro;