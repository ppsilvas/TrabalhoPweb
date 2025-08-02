import {BrowserRouter,Routes,Route} from 'react-router-dom';
import Cadastro from '../Cadastro'

function AppRoutes(){
    return(
        <BrowserRouter>
            <Routes>
                <Route path="/cadastro" element={<Cadastro />}/>
            </Routes>
        </BrowserRouter>
    )
}export default AppRoutes;