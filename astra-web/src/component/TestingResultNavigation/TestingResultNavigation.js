import {mapCompletedToNavigationItem} from "../../mapper/test.mapper";
import withScrollTopButton from "../hoc/withScrollTopButton/withScrollTopButton";
import TestingNavigation from "../TestingNavigation/TestingNavigation";
import "./TestingResultNavigation.css";

const TestingResultNavigation = ({tests}) => {
    const scrollToTest = index => {
        const id = "test" + tests[index].id;
        document.getElementById(id).scrollIntoView();
    }

    const items = tests.map(mapCompletedToNavigationItem);

    return (
        <TestingNavigation
            items={items}
            onSelect={scrollToTest}/>
    );
}

export default withScrollTopButton(TestingResultNavigation);
