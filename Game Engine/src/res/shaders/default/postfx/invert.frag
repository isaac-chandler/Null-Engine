#version 150 core
in vec2 texCoord;

out vec4 outColor;

uniform sampler2D colors;

void main() {
	outColor = texture(colors, texCoord);
	outColor = vec4(1 - outColor.rgb, outColor.a);
}
