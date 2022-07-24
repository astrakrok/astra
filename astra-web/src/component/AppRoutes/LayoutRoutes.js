import {Route, Routes} from "react-router-dom";
import {page} from "../../constant/page";
import PopupProvider from "../../context/popup/PopupProvider";
import Footer from "../Footer/Footer";
import Header from "../Header/Header";
import AllSpecializationsPage from "../page/admin/specializations/all/AllSpecializationsPage";
import AllSubjectsPage from "../page/admin/subjects/all/AllSubjectsPage";
import AllTestsPage from "../page/admin/tests/all/AllTestsPage";
import HomePage from "../page/home/HomePage";
import LoginPage from "../page/login/LoginPage";
import RegisterPage from "../page/register/RegisterPage";
import CreateTestPage from "../page/admin/tests/create/CreateTestPage";
import PermissionBoundary from "../PermissionBoundary/PermissionBoundary";
import AllExamsPage from "../page/admin/exams/all/AllExamsPage";

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
                        <Route path={page.admin.subjects.all} element={<AllSubjectsPage />} />
                        <Route path={page.admin.tests.all} element={<AllTestsPage />} />
                        <Route path={page.admin.tests.create} element={<CreateTestPage />} />
                        <Route path={page.admin.exams.all} element={<AllExamsPage />} />
                    </Routes>
                </main>

                <Footer />
            </PopupProvider>
        </PermissionBoundary>
    );
}
