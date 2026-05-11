Feature: Event Booking

  Scenario: Successful Ticket Reservation
    Given an event with 10 available seats exists
    And a registered user exists
    When the user reserves a ticket
    Then the reservation should be successful
    And the event should have 9 available seats

  Scenario: Failed reservation when no seats remain
    Given an event with 1 available seats exists
    And two registered users exist
    When the first user reserves the last ticket
    Then the second user should not be able to reserve a ticket
    And the event should have 0 available seats

  Scenario: Cancelling an existing reservation
    Given an event with 10 available seats exists
    And a registered user exists
    And the user has reserved a ticket
    When the user cancels the reservation
    Then the reservation should be cancelled
    And the event should have 10 available seats

  Scenario: Transferring a reservation to another user
    Given an event with 10 available seats exists
    And two registered users exist
    And the first user has reserved a ticket
    When the reservation is transferred to the second user
    Then the reservation should belong to the second user
    And the event should still have 9 available seats