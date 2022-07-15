import { Routes, Route } from "react-router-dom";
import { page } from "../../constant/page";
import PopupProvider from "../../context/popup/PopupProvider";
import Footer from "../Footer/Footer";
import Header from "../Header/Header";
import AllSpecializationsPage from "../page/admin/specializations/all/AllSpecializationsPage";
import HomePage from "../page/home/HomePage";
import LoginPage from "../page/login/LoginPage";
import RegisterPage from "../page/register/RegisterPage";
import PermissionBoundary from "../PermissionBoundary/PermissionBoundary";

export const LayoutRoutes = () => {
    return (
        <PermissionBoundary>
            <PopupProvider>
                <Header />

                <main className="s-vflex-center">
                    <Routes>
                        <Route path={page.home} element={<HomePage />} />
                        <Route path={page.login} element={<LoginPage />} />
                        <Route path={page.register} element={<RegisterPage />} />
                        <Route path={page.admin.specializations.all} element={<AllSpecializationsPage />} />
                    </Routes>
                </main>

                <Footer />
            </PopupProvider>
        </PermissionBoundary>
    );
}
