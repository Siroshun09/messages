plugins {
    id ("messages.common-conventions")
}

afterEvaluate {
    collector.JavadocAggregator.addProject(this)
}
