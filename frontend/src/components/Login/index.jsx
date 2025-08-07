import axios from '../../services/api';
import { useState } from 'react';
import { Link, useNavigate, useLocation } from 'react-router-dom';
import useAuth from '../../hooks/useAuth';
import { jwtDecode } from 'jwt-decode';
import { Mail, Lock, Eye, EyeOff, Building2 } from 'lucide-react';

function Login() {
    const { setAuth } = useAuth();

    const navigate = useNavigate()
    const location = useLocation();
    const from = location.state?.from?.pathname || '/';

    const [email, setEmail] = useState('');
    const [senha, setSenha] = useState('');
    const [erro, setErro] = useState('');
    const [showPassword, setShowPassword] = useState(false);
    const [isLoading, setIsLoading] = useState(false);

    const LOGIN_URL = '/auth';

    const handleSubmit = async (event) => {
        event.preventDefault();
        setIsLoading(true);
        try {
            const res = await axios.post(LOGIN_URL,
                { email, senha },
                {
                    headers: { 'Content-Type': 'application/json' },
                    //withCredentials:true
                });
            const token = res.data.token;
            const decoded = jwtDecode(token);
            const id = decoded.userId;
            setAuth({ accessToken: token, id });
            navigate(from, { replace: true });
            console.log(res.data);
        } catch (err) {
            setErro('E-mail ou senha inválidos')
            console.error(err);
        }
    }

    //TODO: Estilizar
    return (
        <div className="min-h-screen bg-gradient-to-br from-purple-50 via-white to-purple-100 flex items-center justify-center px-4 sm:px-6 lg:px-8">
            <div className="max-w-md w-full space-y-8">
                {/* Header */}
                <div className="text-center">
                    <div className="flex justify-center mb-6">
                        <div className="bg-purple-600 p-3 rounded-full shadow-lg">
                            <Building2 className="h-8 w-8 text-white" />
                        </div>
                    </div>
                    <h2 className="text-3xl font-bold text-gray-900 mb-2">IBanking</h2>
                    <p className="text-sm text-gray-600">Acesse sua conta com segurança</p>
                </div>

                {/* Login Form */}
                <div className="bg-white py-8 px-6 shadow-xl rounded-2xl border border-gray-100">
                    <div className="space-y-6">
                        {/* Email Field */}
                        <div>
                            <label htmlFor="email" className="block text-sm font-medium text-gray-700 mb-2">
                                E-mail
                            </label>
                            <div className="relative">
                                <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                                    <Mail className="h-5 w-5 text-gray-400" />
                                </div>
                                <input
                                    id="email"
                                    name="email"
                                    type="email"
                                    autoComplete="email"
                                    required
                                    value={email}
                                    onChange={(event) => setEmail(event.target.value)}
                                    className="block w-full pl-10 pr-3 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-purple-500 focus:border-transparent transition-all duration-200 text-gray-900 placeholder-gray-500"
                                    placeholder="seu@email.com"
                                />
                            </div>
                        </div>

                        {/* Password Field */}
                        <div>
                            <label htmlFor="senha" className="block text-sm font-medium text-gray-700 mb-2">
                                Senha
                            </label>
                            <div className="relative">
                                <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                                    <Lock className="h-5 w-5 text-gray-400" />
                                </div>
                                <input
                                    id="senha"
                                    name="password"
                                    type={showPassword ? "text" : "password"}
                                    autoComplete="current-password"
                                    required
                                    value={senha}
                                    onChange={(event) => setSenha(event.target.value)}
                                    className="block w-full pl-10 pr-12 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-purple-500 focus:border-transparent transition-all duration-200 text-gray-900 placeholder-gray-500"
                                    placeholder="Digite sua senha"
                                />
                                <button
                                    type="button"
                                    className="absolute inset-y-0 right-0 pr-3 flex items-center"
                                    onClick={() => setShowPassword(!showPassword)}
                                >
                                    {showPassword ? (
                                        <EyeOff className="h-5 w-5 text-gray-400 hover:text-gray-600 transition-colors" />
                                    ) : (
                                        <Eye className="h-5 w-5 text-gray-400 hover:text-gray-600 transition-colors" />
                                    )}
                                </button>
                            </div>
                        </div>

                        {/* Error Message */}
                        {erro && (
                            <div className="bg-red-50 border border-red-200 rounded-lg p-3">
                                <p className="text-sm text-red-600 text-center">{erro}</p>
                            </div>
                        )}

                        {/* Submit Button */}
                        <div>
                            <button
                                type="button"
                                onClick={handleSubmit}
                                disabled={isLoading}
                                className="group relative w-full flex justify-center py-3 px-4 border border-transparent text-sm font-medium rounded-lg text-white bg-purple-600 hover:bg-purple-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-purple-500 disabled:opacity-50 disabled:cursor-not-allowed transition-all duration-200 transform hover:scale-[1.02] active:scale-[0.98]"
                            >
                                {isLoading ? (
                                    <div className="flex items-center">
                                        <div className="animate-spin rounded-full h-4 w-4 border-b-2 border-white mr-2"></div>
                                        Entrando...
                                    </div>
                                ) : (
                                    'Entrar'
                                )}
                            </button>
                        </div>
                    </div>

                    {/* Sign Up Link */}
                    <div className="mt-6 pt-6 border-t border-gray-200">
                        <p className="text-center text-sm text-gray-600">
                            Não tem uma conta?{' '}
                            <button
                                onClick={() => window.location.href = '/cadastro'}
                                className="font-medium text-purple-600 hover:text-purple-800 transition-colors duration-200"
                            >
                                Cadastre-se já!
                            </button>
                        </p>
                    </div>
                </div>

                {/* Footer */}
                <div className="text-center">
                    <p className="text-xs text-gray-500">
                        © 2025 IBanking. Todos os direitos reservados.
                    </p>
                </div>
            </div>
        </div>
    );
} export default Login;