import { Link } from 'react-router-dom';
import { page } from '../../constant/page';
import './Header.css';

export const Header = () => {
    return (
        <header className="full-width">
            <div className="container">
                <div className="row">
                    <div className="s-hflex-end">
                        <Link to={page.register} className="register weight-strong">Зареєструватися</Link>
                        <Link to={page.login} className="login weight-strong">Увійти</Link>
                    </div>
                </div>
            </div>
        </header>
    );
}