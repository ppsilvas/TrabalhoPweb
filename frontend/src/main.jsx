import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import AppRoutes from './components/AppRoutes'
import { worker } from './mocks/browser';
import { AuthProvider } from './context/AuthProvider';
import './index.css';

if (import.meta.env.DEV) {
  //worker.start().then(() => {
    //console.log('MSW iniciado');
    createRoot(document.getElementById('root')).render(
      <AuthProvider>
        <AppRoutes />
      </AuthProvider>);
  //});
} else {
  createRoot(document.getElementById('root')).render(
    <StrictMode>
      <AuthProvider>
        <AppRoutes />
      </AuthProvider>
    </StrictMode>,
  )
}

