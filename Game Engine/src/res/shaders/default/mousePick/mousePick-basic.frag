#version 150 core

in vec4 worldPosition;
in vec3 localPosition;

out vec4 outColor;
out vec4 outWorldPosition;
out vec4 outLocalPosition;

uniform vec4 color;

void main() {
	outColor = color;
	outWorldPosition = worldPosition;
	outLocalPosition = vec4(localPosition, 1);
}
