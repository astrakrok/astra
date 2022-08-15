import {Route, Routes} from "react-router-dom";
import AuthProvider from "../../context/auth/AuthProvider";
import PopupProvider from "../../context/popup/PopupProvider";
import AxiosClient from "../AxiosClient/AxiosClient";
import PermissionBoundary from "../PermissionBoundary/PermissionBoundary";
import ErrorBoundary from "../ErrorBoundary/ErrorBoundary";
import {LayoutRoutes} from "./LayoutRoutes";

export const AppRoutes = () => {
    return (
        <AuthProvider>
            <PopupProvider>
                <ErrorBoundary>
                    <AxiosClient>
                        <PermissionBoundary>
                            <Routes>
                                <Route path="/*" element={<LayoutRoutes />} />
                            </Routes>
                        </PermissionBoundary>
                    </AxiosClient>
                </ErrorBoundary>
            </PopupProvider>
        </AuthProvider>
    );
}
