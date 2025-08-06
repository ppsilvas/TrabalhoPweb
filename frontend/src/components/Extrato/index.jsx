import useAxiosPrivate from "../../hooks/useAxiosPrivate";
import useAuth from "../../hooks/useAuth";
import { useState } from "react";


function Extrato({ OPERACOES_URL }) {

    const { auth } = useAuth();
    const axiosPrivate = useAxiosPrivate();

    const filtros = ['Deposito', 'Saque', 'Pagamento'];
    const [filtroSelecionado, setFiltroSelecionado] = useState('Pagamento');
    const [dataInicio, setDataInicio] = useState('');
    const [dataFim, setDataFim] = useState('');
    const [extrato, setExtrato] = useState('');
    const [erro, setErro] = useState('');

    const usuarioId = auth.id;

    const fetchExtrato = async () => {
        try {
            const response = await axiosPrivate.get(OPERACOES_URL + `/${usuarioId}/extrato`,
                { tipo: filtroSelecionado, dataInicio, dataFim, id: usuarioId }
            );
            setExtrato(response.data);
            setErro('');
        }
        catch (err) {
            console.error(err);
            setErro('Erro ao carregar extrato');
            setExtrato('');
        }
    };

    return (
        <>
            <div>

                <label htmlFor="">
                    Tipo de operação:
                </label>
                <select
                    id="filtro"
                    value={filtroSelecionado}
                    onChange={(event) => setFiltroSelecionado(event.target.value)}
                    required
                >
                    {filtros.map(f => (
                        <option
                            key={f.indexOf}
                            value={f}
                        >
                            {f}
                        </option>
                    ))}
                </select>

                <label htmlFor="dataInicio">
                    A partir de:
                </label>
                <input
                    type="date"
                    value={dataInicio}
                    onChange={(event) => setDataInicio(event.target.value)}
                    id="dataInicio"
                    autoComplete="off"
                    required
                />

                <label htmlFor="dataFim">
                    Até:
                </label>
                <input
                    type="date"
                    value={dataFim}
                    onChange={(event) => setDataFim(event.target.value)}
                    id="dataFim"
                    autoComplete="off"
                    required
                />

                <button onClick={fetchExtrato}>Aplicar Filtro</button>

                {extrato && extrato.length > 0 && (
                    <table>
                        <thead>
                            <tr>
                                <th>Tipo</th>
                                <th>Número</th>
                                <th>Agência</th>
                                <th>Valor</th>
                                <th>Data</th>
                                <th>Descrição</th>
                            </tr>
                        </thead>
                        <tbody>
                            {extrato.map((op, index) => (
                                <tr key={index}>
                                    <td>{op.tipo}</td>
                                    <td>{op.numero}</td>
                                    <td>{op.agencia}</td>
                                    <td>R$ {parseFloat(op.valor).toFixed(2)}</td>
                                    <td>{new Date(op.dataOperacao).toLocaleString('pt-BR')}</td>
                                    <td>{op.descricao}</td>
                                </tr>
                            ))}
                        </tbody>
                    </table>
                )}

                {erro && <p className="erro">{erro}</p>}
            </div>
        </>
    );
} export default Extrato;