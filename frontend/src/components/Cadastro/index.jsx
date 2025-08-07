import axios from '../../services/api';
import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { Mail, Lock, Eye, EyeOff, Building2, User, CreditCard } from 'lucide-react';

function Cadastro() {

    const [nome, setNome] = useState('');
    const [email, setEmail] = useState('');
    const [cpf, setCpf] = useState('');
    const [senha, setSenha] = useState('');
    const [erro, setErro] = useState('');
    const [showPassword, setShowPassword] = useState(false);
    const [isLoading, setIsLoading] = useState(false);

    const REGISTER_URL = '/usuarios';
    let navigate = useNavigate();

    const formatCPF = (value) => {
        const numericValue = value.replace(/\D/g, '');
        return numericValue
            .replace(/(\d{3})(\d)/, '$1.$2')
            .replace(/(\d{3})(\d)/, '$1.$2')
            .replace(/(\d{3})(\d{1,2})/, '$1-$2')
            .replace(/(-\d{2})\d+?$/, '$1');
    };

    const handleCpfChange = (event) => {
        const formatted = formatCPF(event.target.value);
        setCpf(formatted);
    };

    const handleSubmit = async (event) => {
        event.preventDefault();
        setIsLoading(true);
        try {
            const response = await axios.post(REGISTER_URL,
                { nome, email, cpf, senha },
                {
                    headers: { 'Content-Type': 'application/json' },
                    withCredentials: true
                }
            );
            navigate('/login');
            console.log(response.data);
        }
        catch (err) {
            setErro('Cadastro inválido! Teste novamente');
            console.error(err);
        }
    }

    //TODO: Estilizar
    return (
        <div className="min-h-screen bg-gradient-to-br from-purple-50 via-white to-purple-100 flex items-center justify-center px-4 sm:px-6 lg:px-8 py-12">
            <div className="max-w-md w-full space-y-8">
                {/* Header */}
                <div className="text-center">
                    <div className="flex justify-center mb-6">
                        <div className="bg-purple-600 p-3 rounded-full shadow-lg">
                            <Building2 className="h-8 w-8 text-white" />
                        </div>
                    </div>
                    <h2 className="text-3xl font-bold text-gray-900 mb-2">Criar Conta</h2>
                    <p className="text-sm text-gray-600">Junte-se ao IBanking hoje mesmo</p>
                </div>

                {/* Cadastro Form */}
                <div className="bg-white py-8 px-6 shadow-xl rounded-2xl border border-gray-100">
                    <div className="space-y-6">
                        {/* Nome Field */}
                        <div>
                            <label htmlFor="nome" className="block text-sm font-medium text-gray-700 mb-2">
                                Nome Completo
                            </label>
                            <div className="relative">
                                <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                                    <User className="h-5 w-5 text-gray-400" />
                                </div>
                                <input
                                    id="nome"
                                    name="nome"
                                    type="text"
                                    autoComplete="name"
                                    required
                                    value={nome}
                                    onChange={(event) => setNome(event.target.value)}
                                    className="block w-full pl-10 pr-3 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-purple-500 focus:border-transparent transition-all duration-200 text-gray-900 placeholder-gray-500"
                                    placeholder="Seu nome completo"
                                />
                            </div>
                        </div>

                        {/* CPF Field */}
                        <div>
                            <label htmlFor="cpf" className="block text-sm font-medium text-gray-700 mb-2">
                                CPF
                            </label>
                            <div className="relative">
                                <div className="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                                    <CreditCard className="h-5 w-5 text-gray-400" />
                                </div>
                                <input
                                    id="cpf"
                                    name="cpf"
                                    type="text"
                                    autoComplete="off"
                                    required
                                    value={cpf}
                                    onChange={handleCpfChange}
                                    maxLength={14}
                                    className="block w-full pl-10 pr-3 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-purple-500 focus:border-transparent transition-all duration-200 text-gray-900 placeholder-gray-500"
                                    placeholder="000.000.000-00"
                                />
                            </div>
                        </div>

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
                                    autoComplete="new-password"
                                    required
                                    value={senha}
                                    onChange={(event) => setSenha(event.target.value)}
                                    className="block w-full pl-10 pr-12 py-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-purple-500 focus:border-transparent transition-all duration-200 text-gray-900 placeholder-gray-500"
                                    placeholder="Crie uma senha segura"
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
                            <p className="mt-2 text-xs text-gray-500">
                                Use pelo menos 8 caracteres com letras e números
                            </p>
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
                                        Cadastrando...
                                    </div>
                                ) : (
                                    'Criar Conta'
                                )}
                            </button>
                        </div>

                        {/* Terms */}
                        <div className="text-center">
                            <p className="text-xs text-gray-500">
                                Ao criar sua conta, você concorda com nossos{' '}
                                <a href="#" className="text-purple-600 hover:text-purple-800 transition-colors">
                                    Termos de Uso
                                </a>{' '}
                                e{' '}
                                <a href="#" className="text-purple-600 hover:text-purple-800 transition-colors">
                                    Política de Privacidade
                                </a>
                            </p>
                        </div>
                    </div>

                    {/* Login Link */}
                    <div className="mt-6 pt-6 border-t border-gray-200">
                        <p className="text-center text-sm text-gray-600">
                            Já tem uma conta?{' '}
                            <button
                                onClick={() => window.location.href = '/login'}
                                className="font-medium text-purple-600 hover:text-purple-800 transition-colors duration-200"
                            >
                                Faça login
                            </button>
                        </p>
                    </div>
                </div>

                {/* Footer */}
                <div className="text-center">
                    <p className="text-xs text-gray-500">
                        © 2024 IBanking. Todos os direitos reservados.
                    </p>
                </div>
            </div>
        </div>
    );
} export default Cadastro;