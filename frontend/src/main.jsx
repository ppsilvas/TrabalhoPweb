import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import AppRoutes from './components/AppRoutes'
import { worker } from './mocks/browser';

if (import.meta.env.DEV) {
  worker.start().then(() => {
    console.log('MSW iniciado');
    createRoot(document.getElementById('root')).render(<AppRoutes />);
  });
} else {
  createRoot(document.getElementById('root')).render(
    <StrictMode>
      <AppRoutes />
    </StrictMode>,
  )
}

