import axios from '../../services/api';
import {useState} from 'react';
import {useNavigate} from 'react-router-dom';

function Login(){

    const [usuario,setUsuario] = useState({
        email:'',
        senha:''
    })

}export default Login;