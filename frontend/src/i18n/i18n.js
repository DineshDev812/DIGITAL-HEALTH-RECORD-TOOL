import i18n from 'i18next';
import { initReactI18next } from 'react-i18next';
import en from './locales/en.json';
import ml from './locales/ml.json';
import hi from './locales/hi.json';
import ta from './locales/ta.json';
import bn from './locales/bn.json';

const savedLanguage = localStorage.getItem('language');

i18n.use(initReactI18next).init({
  resources: { en: { translation: en }, ml: { translation: ml }, hi: { translation: hi }, ta: { translation: ta }, bn: { translation: bn } },
  lng: savedLanguage || 'en',
  fallbackLng: 'en',
  interpolation: { escapeValue: false },
});

i18n.on('languageChanged', (language) => localStorage.setItem('language', language));

export default i18n;
