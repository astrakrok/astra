import { AuthControl } from '../AuthControl/AuthControl';
import { Logo } from '../Logo/Logo';
import './Header.css';

export const Header = () => {
    return (
        <header className="full-width Header">
            <div className="container">
                <div className="row content">
                    <div className="s-hflex-start">
                        <Logo />
                        <div className="equal-flex" />
                        <AuthControl />
                    </div>
                </div>
            </div>
        </header>
    );
}
