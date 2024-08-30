abstract class AggregateRoot<ID : Identifier> protected constructor(
    id: ID
) : Entity<ID>(id)