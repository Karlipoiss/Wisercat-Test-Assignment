import React, { Component } from 'react';
import { Link, withRouter } from 'react-router-dom';
import {
    Alert,
    Button,
    Card,
    CardBody,
    CardFooter,
    CardHeader,
    Form,
    FormGroup,
    Input,
    Label,
    Table
} from 'reactstrap';

class FilterEdit extends Component {
    emptyItem = {
        name: '',
        criteriaList: [],
    };

    constructor(props) {
        super(props);
        this.state = {
            item: this.emptyItem,
            alerts: []
        };
        this.handleNameChange = this.handleNameChange.bind(this);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    async componentDidMount() {
        if (this.props.match.params.id !== 'new') {
            const filter = await (await fetch(`/filters/${this.props.match.params.id}`)).json();
            this.setState({item: filter});
        }
    }

    handleNameChange(event) {
        const target = event.target;
        const value = target.value;
        const name = target.name;
        let item = {...this.state.item};
        item[name] = value;
        this.setState({item});
    }

    async handleSubmit(event) {
        event.preventDefault();
        const {item} = this.state;
        this.setState({alerts: []})

        await fetch('/filters' + (item.id ? '/' + item.id : ''), {
            method: (item.id) ? 'PUT' : 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(item),
        }).then(response => response.json())
            .then(data => {
                if ('error' in data) {
                    this.setState({alerts: [{type: 'danger', text: data['error']}]})
                } else {
                    this.setState({alerts: [{type: 'success', text: 'Filter saved'}]})
                }
            })
    }

    criteriaComparisonOptions () {
        return {
            'AMOUNT': [
                {'key': 'MORE', 'name': 'More'},
                {'key': 'LESS', 'name': 'Less'},
                {'key': 'EQUAL', 'name': 'Equals'},
            ],
            'TITLE': [
                {'key': 'STARTS', 'name': 'Starts with'},
                {'key': 'ENDS', 'name': 'Ends with'},
                {'key': 'EQUAL', 'name': 'Equals'},
            ],
            'DATE': [
                {'key': 'FROM', 'name': 'From'},
                {'key': 'TO', 'name': 'To'},
                {'key': 'EQUAL', 'name': 'Equals'},
            ]
        };
    }

    criteriaValueTypes () {
        return {
            'AMOUNT': 'number',
            'TITLE': 'text',
            'DATE': 'date'
        };
    }

    handleCriteriaAdd() {
        let item = this.state.item;
        item.criteriaList.push({'type': 'AMOUNT', 'comparison': 'MORE', 'textValue': '', 'numberValue': '', 'dateValue': ''});

        this.setState({
            item: item
        });
    }

    handleCriteriaChanged(i, inputType, event) {
        let item = this.state.item;

        item.criteriaList[i][inputType] = event.target.value;
        if (inputType === 'type') {
            item.criteriaList[i]['comparison'] = this.criteriaComparisonOptions()[event.target.value][0].key;
            item.criteriaList[i]['textValue'] = '';
            item.criteriaList[i]['numberValue'] = '';
            item.criteriaList[i]['dateValue'] = '';
        }

        this.setState({
            item: item
        });
    }

    handleCriteriaDeleted(i) {
        let item = this.state.item;
        item.criteriaList.splice(i, 1);

        this.setState({
            items: item
        });
    }

    renderCriteriaRows() {
        let context = this;

        return  this.state.item.criteriaList.map(function(o, i) {
            let typeSelected = o ? o.type : 'AMOUNT';
            let valueType = context.criteriaValueTypes()[typeSelected];
            let value = o ? o[valueType + 'Value'] : '';
            if (valueType === 'date' && value)
                value = new Date(value).toISOString().slice(0, 10);

            return (
                <tr key={"item-" + i}>
                    <td>
                        <Input type="select" value={typeSelected} onChange={context.handleCriteriaChanged.bind(context, i, 'type')}>
                            <option value="AMOUNT">Amount</option>
                            <option value="TITLE">Title</option>
                            <option value="DATE">Date</option>
                        </Input>
                    </td>
                    <td>
                        <Input type="select" value={o ? o.comparison : ''} onChange={context.handleCriteriaChanged.bind(context, i, 'comparison')}>
                            {context.criteriaComparisonOptions()[typeSelected].map((el) => <option key={el.key} value={el.key}>{el.name}</option>)}
                        </Input>
                    </td>
                    <td>
                        <Input type={valueType} value={value}
                               onChange={context.handleCriteriaChanged.bind(context, i, valueType + 'Value')}>
                        </Input>
                    </td>
                    <td>
                        <Button color="danger"
                                onClick={context.handleCriteriaDeleted.bind(context, i)}
                        >
                            Delete
                        </Button>
                    </td>
                </tr>
            );
        });
    };

    renderCriteriaTable() {
        return (
            <div>
                <Table>
                    <thead>
                    </thead>
                    <tbody>
                    {this.renderCriteriaRows()}
                    </tbody>
                </Table>
                <hr/>
                <div className="text-center">
                    <Button color="success"
                            onClick={this.handleCriteriaAdd.bind(this)}
                    >
                        Add Item
                    </Button>
                </div>
            </div>);
    };

    render() {
        const {item, alerts} = this.state;
        const title = <h2>{item.id ? 'Edit Filter' : 'Add Filter'}</h2>;
        const renderAlerts = alerts.map(filter => {
            return <Alert color={filter.type}>{filter.text}</Alert>
        });

        return <div className={"filterEdit"}>
            <Form onSubmit={this.handleSubmit}>
            <Card style={{width:'1000px', height:'600px', resize: 'vertical', overflow:'auto'}}>
                    <CardHeader>
                        {title}
                    </CardHeader>
                    <CardBody style={{overflow:'auto'}}>
                        <div className={"filterEditAlerts"}>
                            {renderAlerts}
                        </div>
                        <FormGroup>
                            <Label for="name">Name</Label>
                            <Input type="text" name="name" id="name" value={item.name || ''}
                                   onChange={this.handleNameChange} autoComplete="name"/>
                        </FormGroup>
                        <FormGroup>
                            <Label for="criteriaTable">Criterias</Label>
                            {this.renderCriteriaTable()}
                        </FormGroup>
                    </CardBody>
                    <CardFooter>
                        <div className="text-center">
                            <Button color="primary" type="submit">Save</Button>{' '}
                            <Button color="secondary" tag={Link} to="/filters">Cancel</Button>
                        </div>
                    </CardFooter>
                </Card>
            </Form>
        </div>
    }
}
export default withRouter(FilterEdit);