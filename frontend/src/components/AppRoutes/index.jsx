import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Cadastro from '../Cadastro'
import Login from '../Login'
import ContaCorrente from '../ContaCorrente';
import RequireAuth from '../RequireAuth';

function AppRoutes() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/cadastro" element={<Cadastro />} />
                <Route path="/login" element={<Login />} />
                <Route element={<RequireAuth />}>
                    <Route path="/contacorrente" element={<ContaCorrente />} />
                </Route>
            </Routes>
        </BrowserRouter>
    )
} export default AppRoutes;