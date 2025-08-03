import {BrowserRouter,Routes,Route} from 'react-router-dom';
import Cadastro from '../Cadastro'
import Login from '../Login'

function AppRoutes(){
    return(
        <BrowserRouter>
            <Routes>
                <Route path="/cadastro" element={<Cadastro />}/>
                <Route path="/login" element={<Login />}/>
            </Routes>
        </BrowserRouter>
    )
}export default AppRoutes;