import { useEffect, useState } from 'react';
import useAxiosPrivate from '../../hooks/useAxiosPrivate';
import useAuth from '../../hooks/useAuth';
import Operacoes from '../Operacoes';
import { Building2, Eye, EyeOff, User, Hash, MapPin, DollarSign } from 'lucide-react';

function ContaCorrente() {
    const { auth } = useAuth();
    const [conta, setConta] = useState(null);
    const [erro, setErro] = useState('');
    const [showSaldo, setShowSaldo] = useState(true);

    const axiosPrivate = useAxiosPrivate();

    const fetchConta = async () => {
        try {
            const response = await axiosPrivate.get(`/conta/${auth.id}`, {
                headers: {
                    Authorization: `Bearer ${auth.accessToken}`
                }
            });
            setConta(response.data);
        }
        catch (err) {
            console.error(err);
            setErro('Erro ao carregar conta');
        }
    };

    useEffect(() => {
        fetchConta();
    }, [auth]);

    const formatCurrency = (value) => {
        return new Intl.NumberFormat('pt-BR', {
            style: 'currency',
            currency: 'BRL'
        }).format(value);
    };

    if (erro) return <p>{erro}</p>
    if (!conta) return <p>Carregando...</p>

    return (
        <section className="min-h-screen bg-gradient-to-br from-purple-50 via-white to-purple-100 p-4 sm:p-6 lg:p-8">
            <div className="max-w-4xl mx-auto space-y-6">

                {/* Header com nome do usuário */}
                <div className="bg-white rounded-xl shadow-lg border border-gray-100 p-6">
                    <div className="flex items-center justify-between mb-4">
                        <div className="flex items-center space-x-3">
                            <div className="bg-purple-600 p-2 rounded-lg">
                                <Building2 className="h-6 w-6 text-white" />
                            </div>
                            <div>
                                <p className="text-sm text-gray-600">IBanking</p>
                                <h1 className="text-2xl font-bold text-gray-900">{conta.usuario}</h1>
                            </div>
                        </div>
                    </div>

                    <div className="bg-gradient-to-r from-purple-500 to-purple-600 rounded-lg p-4">
                        <div className="flex items-center space-x-2">
                            <User className="h-5 w-5 text-white" />
                            <span className="text-white text-sm opacity-90">Conta Corrente</span>
                        </div>
                    </div>
                </div>

                {/* Cards com informações da conta */}
                <div className="grid grid-cols-1 md:grid-cols-3 gap-6">

                    {/* Card Número */}
                    <div className="bg-white rounded-xl shadow-sm border border-gray-100 p-6">
                        <div className="flex items-center space-x-2 mb-3">
                            <Hash className="h-5 w-5 text-blue-600" />
                            <span className="text-sm font-medium text-gray-700">Número</span>
                        </div>
                        <p className="text-xl font-bold text-gray-900">{conta.numero}</p>
                    </div>

                    {/* Card Agência */}
                    <div className="bg-white rounded-xl shadow-sm border border-gray-100 p-6">
                        <div className="flex items-center space-x-2 mb-3">
                            <MapPin className="h-5 w-5 text-purple-600" />
                            <span className="text-sm font-medium text-gray-700">Agência</span>
                        </div>
                        <p className="text-xl font-bold text-gray-900">{conta.agencia}</p>
                    </div>

                    {/* Card Saldo */}
                    <div className="bg-white rounded-xl shadow-sm border border-gray-100 p-6">
                        <div className="flex items-center justify-between mb-3">
                            <div className="flex items-center space-x-2">
                                <DollarSign className="h-5 w-5 text-green-600" />
                                <span className="text-sm font-medium text-gray-700">Saldo</span>
                            </div>
                            <button
                                onClick={() => setShowSaldo(!showSaldo)}
                                className="text-gray-400 hover:text-gray-600 transition-colors"
                            >
                                {showSaldo ? <EyeOff className="h-4 w-4" /> : <Eye className="h-4 w-4" />}
                            </button>
                        </div>
                        <p className="text-xl font-bold text-green-600">
                            {showSaldo ? formatCurrency(conta.saldo) : '••••••'}
                        </p>
                    </div>

                </div>

                {/* Componente Operações */}
                <Operacoes onOperacaoConcluida={fetchConta} OPERACOES_URL='/operacoes' />

            </div>
        </section>
    );
} export default ContaCorrente;