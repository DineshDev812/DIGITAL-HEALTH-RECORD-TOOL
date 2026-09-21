import React from 'react';
import { createRoot } from 'react-dom/client';
import App from './App';
import { ThemeProvider } from './ThemeContext';
import './i18n/i18n';
import './styles.css';
import './header-controls.css';

createRoot(document.getElementById('root')).render(<React.StrictMode><ThemeProvider><App /></ThemeProvider></React.StrictMode>);
