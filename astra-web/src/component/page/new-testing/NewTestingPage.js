import SelectTestingOptionsForm from "../../form/SelectTestingOptionsForm/SelectTestingOptionsForm";
import withTitle from "../../hoc/withTitle/withTitle";
import {useNavigate} from "react-router-dom";
import withAllAvailableHeight from "../../hoc/withAllAvailableHeight/withAllAvailableHeight";
import "./NewTestingPage.css";
import withExams from "../../hoc/withExams/withExams";
import {page} from "../../../constant/page";

const NewTestingPage = () => {
    const navigate = useNavigate();

    const Form = withExams(SelectTestingOptionsForm);

    const applyFilters = options => {
        const search = `?mode=${options.mode}&testingId=${options.testingId}` +
            (options.count ? `&count=${options.count}` : "");
        navigate({
            pathname: page.testing,
            search
        });
    }

    return (
        <div className="container">
            <div className="row">
                <div className="s-hflex-center">
                    <Form onSelect={applyFilters}/>
                </div>
            </div>
        </div>
    );
}

export default withAllAvailableHeight(withTitle(NewTestingPage, "Тестування"));
