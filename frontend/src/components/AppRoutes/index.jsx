import { BrowserRouter, Routes, Route } from 'react-router-dom';
import Cadastro from '../Cadastro'
import Login from '../Login'
import ContaCorrente from '../ContaCorrente';
import RequireAuth from '../RequireAuth';
import Erro from '../Erro';

function AppRoutes() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="*" element={<Erro />} />
                <Route path="/cadastro" element={<Cadastro />} />
                <Route path="/login" element={<Login />} />
                <Route element={<RequireAuth />}>
                    <Route path="/" element={<ContaCorrente />} />
                </Route>
            </Routes>
        </BrowserRouter>
    )
} export default AppRoutes;