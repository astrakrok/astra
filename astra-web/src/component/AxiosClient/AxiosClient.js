import {client} from "../../shared/js/axios";
import {refreshTokens} from "../../service/token.service";
import axios from "axios";
import {route} from "../../constant/app.route";
import {get} from "../../handler/token.handler";
import useAuthorize from "../../hook/useAuthorize";

const tokenType = "Bearer";
const headerKey = "Authorization";

let requestQueue = [];
let isRefreshing = false;

const makeAuthorized = (headers, token) => {
    headers[headerKey] = `${tokenType} ${token}`;
}

const processUnauthorized = async request => {
    const tokenResponse = await refreshTokens();
    const accessToken = tokenResponse.accessToken;
    makeAuthorized(axios.defaults.headers, accessToken);
    requestQueue.forEach(request => request(accessToken));
    requestQueue = [];
    return client(request);
}

const AxiosClient = ({children}) => {
    const authorize = useAuthorize();

    const onRequest = config => {
        const tokenData = get();
        if (tokenData) {
            makeAuthorized(config.headers, tokenData.accessToken);
        }
        return config;
    }

    const onRequestError = error => Promise.reject(error);

    const onResponse = response => response;

    const onResponseError = async error => {
        const request = error.config;

        if (error.response.status === 401 && request.url === route.auth) {
            authorize();
            return Promise.reject(error);
        }

        if (error.response.status === 401 && !request.retry) {
            if (!isRefreshing) {
                isRefreshing = true;
                request.retry = true;
                try {
                    return await processUnauthorized(request);
                } catch (error) {
                    authorize();
                } finally {
                    isRefreshing = false;
                }
            } else {
                return new Promise(resolve => {
                    requestQueue.push(token => {
                        request.baseUrl = "";
                        request.headers.Authorization = token;
                        resolve(client(request));
                    })
                });
            }
        }

        return Promise.reject(error);
    }

    client.interceptors.request.use(onRequest, onRequestError);

    client.interceptors.response.use(onResponse, onResponseError);

    return (
        <>
            {children}
        </>
    );
}

export default AxiosClient;
