import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import './theme.css';
import App from './domain/App/App';
import reportWebVitals from './reportWebVitals';
import {ApiProvider, RealApi} from "./apis/ApiBase/ApiProvider";
import {coursesStoreReducer} from "./storing/coursesStore/coursesStore";
import {configureStore} from "@reduxjs/toolkit";
import { Provider } from 'react-redux';
import {BrowserRouter} from "react-router-dom";


const root = ReactDOM.createRoot(
  document.getElementById('root') as HTMLElement
);

const store = configureStore({
    reducer: {
        courses: coursesStoreReducer,
    }
})
// Infer the `RootState` and `AppDispatch` types from the store itself
export type RootState = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch


root.render(
    <Provider store={store}>
        <ApiProvider apis={RealApi}>
            <BrowserRouter>
                <App />
            </BrowserRouter>
        </ApiProvider>
    </Provider>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
