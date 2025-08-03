function UserForm({usuario, setUsuario, handleSubmit}){

    const handleInput = (event)=>{
        setUsuario({...usuario, [event.target.name]: event.target.event})
    }

    //TODO: Estilizar
    return(
        <form onSubmit={handleSubmit}>
            Nome: <input input type="text" onChange={handleInput} name="nome"></input>
            CPF: <input input type="text" onChange={handleInput} name="cpf"></input>
            E-mail: <input input type="text" onChange={handleInput} name="email"></input>
            Senha: <input input type="text" onChange={handleInput} name="senha"></input>
            <button>Cadastrar</button>
        </form>
    );

}export default UserForm;