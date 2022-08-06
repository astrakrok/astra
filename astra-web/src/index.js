import React from 'react';
import ReactDOM from 'react-dom/client';
import reportWebVitals from './reportWebVitals';
import {BrowserRouter} from 'react-router-dom';
import {AppRoutes} from './component/AppRoutes/AppRoutes';
import './shared/css/common.css';
import './shared/css/site.css';
import {initMessages} from './validation/validator.message';
import {initRules} from './validation/validation.rule';

const root = ReactDOM.createRoot(document.getElementById('root'));

const initApp = () => {
    initRules();
    initMessages();
}

initApp();

root.render(
    <BrowserRouter>
        <AppRoutes />
    </BrowserRouter>
);

reportWebVitals();
