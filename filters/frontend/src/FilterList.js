import React, { Component} from 'react';
import {Button, ButtonGroup, Container, Table} from 'reactstrap';
import { Route, Link, withRouter } from "react-router-dom";
import FilterEdit from "./FilterEdit";
import DialogBox from 'react-modeless';

class FilterList extends Component {

    constructor(props) {
        super(props);
        this.state = {
            filters: [],
            modalDialog:true
        };
        this.remove = this.remove.bind(this);
    }

    componentDidMount() {
        fetch('/filters')
            .then(response => response.json())
            .then(data => this.setState({filters: data}));
    }

    async remove(id) {
        await fetch(`/filters/${id}`, {
            method: 'DELETE',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        }).then(() => {
            let updatedFilters = [...this.state.filters].filter(i => i.id !== id);
            this.setState({filters: updatedFilters});
        });
    }


    render() {
        const {filters, modalDialog} = this.state;

        const dialogStyle = {top: '45%'};
        if (!modalDialog) {
            dialogStyle.border = '1px solid';
        }

        const filterList = filters.map(filter => {
            return <tr key={filter.id}>
                <td style={{whiteSpace: 'nowrap'}}>{filter.name}</td>
                <td>
                        <ButtonGroup>
                            <Button size="sm" color="primary" tag={Link} to={"/filters/" + filter.id}>Edit</Button>
                            <Button size="sm" color="danger" onClick={() => this.remove(filter.id)}>Delete</Button>
                        </ButtonGroup>
                </td>
            </tr>
        });

        return (
            <div className="filterList">
                <Container fluid>
                    <div className="float-right">
                        <ButtonGroup>
                            <Button color="primary" onClick={() => this.setState({modalDialog: true})} active={modalDialog === true}>Modal</Button>
                            <Button color="primary" onClick={() => this.setState({modalDialog: false})} active={modalDialog === false}>Non-Modal</Button>
                        </ButtonGroup>{' '}
                        <Button color="success" tag={Link} to="/filters/new">Add Filter</Button>
                    </div>
                    <h3>Filters</h3>
                    <Table className="mt-4">
                        <thead>
                        <tr>
                            <th width="70%">Name</th>
                            <th width="30%">Actions</th>
                        </tr>
                        </thead>
                        <tbody>
                        {filterList}
                        </tbody>
                    </Table>
                </Container>
                <Route
                    path='/filters/:id'
                    render={() => {
                        return (
                                <DialogBox isOpen={true}
                                           noBackdrop={!modalDialog}
                                           style={dialogStyle}
                                           className={"filterEditDialog"}>
                                    <FilterEdit/>
                                </DialogBox>
                        );
                    }}
                />
            </div>
        );
    }
}
export default withRouter(FilterList);