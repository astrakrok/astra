import React from 'react';
import ReactDOM from 'react-dom/client';
import reportWebVitals from './reportWebVitals';
import { BrowserRouter } from 'react-router-dom';
import { AppRoutes } from './component/AppRoutes/AppRoutes';
import './shared/css/common.css';
import './shared/css/site.css';

const root = ReactDOM.createRoot(document.getElementById('root'));

root.render(
    <BrowserRouter>
        <AppRoutes />
    </BrowserRouter>
);

reportWebVitals();
