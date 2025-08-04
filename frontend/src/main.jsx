import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import AppRoutes from './components/AppRoutes'
import { worker } from './mocks/browser';

if (import.meta.env.DEV) {
  worker.start().then(() => {
    ReactDOM.createRoot(document.getElementById('root')).render(<App />);
  });
} else {
  createRoot(document.getElementById('root')).render(
    <StrictMode>
      <AppRoutes />
    </StrictMode>,
  )
}

